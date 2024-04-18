export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  roles: string[];
}

export interface Review {
  author: string;
  date: string;
  content: string;
  useful: number;
  useless: number;
};
