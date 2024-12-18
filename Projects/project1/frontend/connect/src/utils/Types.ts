export interface Comment {
  id: number,
  content: string,
  user: User,
  likeCount: number,
  likedByUser: boolean,
  timestamp: EpochTimeStamp
}

export interface NewComment {
  content: string,
  post: {
    id: number,
  }
}

export interface Post {
  id: number,
  title: string,
  user: User,
  content: string,
  comments: Comment[], // A post has a list of comments
  timestamp: EpochTimeStamp,
  likeCount: number,
  commentCount: number,
  likedByUser: boolean
}

export interface User {
  id?: number,
  username: string,
  email: string,
  password: string,
  pfp?: Blob,
}

export interface RegisterUser {
  username: string,
  email: string,
  password: string,
}

export interface AuthenticateUser {
  username?: string,
  email?: string,
  password: string,
}

export interface Like {
    
}