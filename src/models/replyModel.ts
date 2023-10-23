import type { CommentModel } from "./commentModel";
import type { UserModel } from "./userModel";

export interface ReplyModel {
  id: string;
  text: string;
  comment: CommentModel;
  createdAt: Date;
  createdBy: UserModel;
}