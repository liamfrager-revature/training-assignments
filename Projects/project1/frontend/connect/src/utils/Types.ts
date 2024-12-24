export interface Comment {
  id: number,
  content: string,
  user: User,
  likeCount: number,
  currentUserLikeID: number | null,
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
  attachment?: string
  comments: Comment[], // A post has a list of comments
  timestamp: EpochTimeStamp,
  likeCount: number,
  commentCount: number,
  currentUserLikeID: number | null
}

export interface NewPost {
  content: string,
  attachment?: string,
}

export interface User {
  id?: number,
  username: string,
  email: string,
  password: string,
  pfp?: string,
  followersCount: number,
  followingCount: number,
}

export interface RegisterUser {
  username: string,
  email: string,
  password: string,
}

export interface UpdateUser {
  username?: string,
  email?: string,
  pfp?: string,
  password?: string,
}

export interface AuthenticateUser {
  username?: string,
  email?: string,
  password: string,
}

export interface SearchResults {
  users: User[],
  posts: Post[],
}