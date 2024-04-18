import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { User } from '../../data/user';
import { MessageService } from '../../services/message.service';
import { TokenStorageService } from '../../services/token-storage.service';
import { Message } from '../../data/message';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-messenger',
  templateUrl: './messenger.component.html',
  styleUrls: ['./messenger.component.scss']
})
export class MessengerComponent implements OnInit, AfterViewChecked {

  private static readonly PAGE_SIZE : number = 25;

  @ViewChild('messagesBox', { static: false }) scrollableDiv?: ElementRef;

  lastUsers : User[] = [];

  form : FormGroup = new FormGroup({
    message : new FormControl('')
  });

  messages : Message[] = [];
  page : number = 0;
  hasElementsLeft : boolean = true;

  userId? : string;

  scrolling : boolean = false;
  loading : boolean = false;

  constructor(private route : ActivatedRoute, private router : Router, private tokenStorage : TokenStorageService, private messageService : MessageService, private userService : UserService) {
    if(!this.tokenStorage.isLoggedIn()){
      this.router.navigate(['/login']);
      return;
    }
  }
  ngAfterViewChecked(): void {
    if(!this.scrolling){
      this.scrollToBottom();
    }
  }

  scrollToBottom(): void {
    try {
        this.scrollableDiv!.nativeElement.scrollTop = this.scrollableDiv?.nativeElement.scrollHeight;
    } catch(err) { }
  }

  ngOnInit(): void {
    this.route.params.subscribe((params : Params) => {
      this.userId = params["id"];

      if(this.userId == this.tokenStorage.getUser().id){
        this.router.navigate(['/message']);
      }

      this.hasElementsLeft = true;
      this.page = 0;
      this.messages = [];
      if(this.userId){
        this.loadMoreMessages();
        this.startMessagePolling();
      }
    });

    this.messageService.getLastUsers().subscribe((users : User[]) => {
      this.lastUsers = users;

      if(this.userId && !this.lastUsers.map(u => u.id).includes(this.userId!)){
        this.userService.getUser(this.userId!).subscribe((user : User) => {
          this.lastUsers.unshift(user);
        });
      }

      if(!this.userId && users.length > 0){
        this.userId = users[0].id;
        this.loadMoreMessages();
        this.startMessagePolling();
      }
    });
  }

  startMessagePolling(){
    setInterval(() => {
      this.loadNewMessages();
    }, 5000);
  }

  sendMessage(){
    if(this.form.get('message')?.value && this.form.get('message')?.value.trim() != '' && this.userId && this.userId != this.tokenStorage.getUser().id){
      this.messageService.sendMessage(this.userId!, this.form.get('message')?.value.trim()).subscribe(() => {
        this.messages.push({
          id : '',
          senderId : this.tokenStorage.getUser().id,
          receiverId : this.userId!,
          senderName : this.tokenStorage.getUser().name,
          receiverName : '',
          content : this.form.get('message')?.value,
          sendTime : Date.now()
        });
        this.scrolling = false;
        this.form.get('message')?.setValue('');
        this.lastUsers.unshift(this.lastUsers.splice(this.lastUsers.map(u => u.id).indexOf(this.userId!), 1)[0]);
      });
    }
  }

  loadMoreMessages() {
    if(this.hasElementsLeft && !this.loading && this.userId && this.userId != this.tokenStorage.getUser().id){
      this.loading = true;
      this.messages.unshift(...Array(MessengerComponent.PAGE_SIZE));

      //this.scrollableDiv!.nativeElement.scrollTop = this.scrollableDiv?.nativeElement.scrollHeight - scrollHeight!;

      this.messageService.getMessages(this.userId!, this.page, MessengerComponent.PAGE_SIZE).subscribe((messages : Message[]) => {
        for(let i = 0; i < messages.length; i++){
          this.messages[i] = messages[messages.length-i-1];
        }

        if(messages.length < MessengerComponent.PAGE_SIZE){
          let diff = MessengerComponent.PAGE_SIZE - messages.length;

          //Remove the skeletons
          this.messages.splice(messages.length, diff);

          this.hasElementsLeft = false;
        }
        ++this.page;
        this.loading = false;
      });
    }
  }

  loadNewMessages(){
    if(this.userId && this.userId != this.tokenStorage.getUser().id){
      this.messageService.getMessages(this.userId!, 0, MessengerComponent.PAGE_SIZE).subscribe((messages : Message[]) => {
        this.messages = this.messages.filter(m => m.id != '');
        let ids = this.messages.map(m => m.id);
        let newMessages : Message[] = messages.filter(m => !ids.includes(m.id));
        this.messages.push(...newMessages);
        this.scrolling = false;
      });
    }
  }

  onScroll(event : any) {
    const scrollableDiv = this.scrollableDiv?.nativeElement;
    const scrollTop = scrollableDiv.scrollTop;
    const scrollHeight = scrollableDiv.scrollHeight;
    const clientHeight = scrollableDiv.clientHeight;

    this.scrolling = true;
    if (scrollTop / (scrollHeight - clientHeight) <= 0.3) {
      this.loadMoreMessages();
    }
    if(scrollTop + clientHeight == scrollHeight){
      this.scrolling = false;
    }
  }

  checkForLoadingMoreMessages(){
    const scrollableDiv = this.scrollableDiv?.nativeElement;
    const scrollTop = scrollableDiv.scrollTop;
    const scrollHeight = scrollableDiv.scrollHeight;
    const clientHeight = scrollableDiv.clientHeight;

    this.scrolling = true;

    if (scrollTop / (scrollHeight - clientHeight) <= 0.3) {
      this.loadMoreMessages();
    }

  }

}
