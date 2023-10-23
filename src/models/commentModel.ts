import type { ReplyModel } from "./replyModel";
import type { UserModel } from "./userModel";

export interface CommentModel {
  id: string;
  text: string;
  user: UserModel;
  createdAt: Date;
  createdBy: UserModel;
  replies: ReplyModel[];
}