export interface HousingListItem {
    id: string;
    title: string;
    authorName: string;
    rating: number;
    image: string;
    imageLoaded?: boolean;
};

export interface Review {
    id: string;
    authorId: string;
    authorName: string;
    time: any;
    housingId: string;
    content: string;
    rating: number;
};

export interface Housing {
    id: string,
    title: string, 
    images: any[],
    rating: number,
    services: string[],
    constraints: string[],
    authorId: string,
    authorName: string,
    description: string,
    landlordDescription: string,
    reviews: Review[]
}