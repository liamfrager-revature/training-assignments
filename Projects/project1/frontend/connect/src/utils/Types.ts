export interface Comment {
  id: number,
  content: string,
  user: User,
  post?: Post, // Optionally include the post if you need it, to avoid circular references.
  likeCount: number,
  likedByUser: boolean,
  timestamp: EpochTimeStamp
}

export interface Post {
  id: number,
  title: string,
  user: User,
  content: string,
  comments: Comment[], // A post has a list of comments
  timestamp: EpochTimeStamp,
  likeCount: number,
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