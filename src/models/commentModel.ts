import type { UserModel, StudentModel } from "./schoolModel";

export interface CommentModel {
  id: string;
  text: string;
  student: StudentModel;
  createdAt: Date;
  createdBy: UserModel;
}