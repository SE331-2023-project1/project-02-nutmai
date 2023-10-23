import type { CommentModel } from "./commentModel";
import type { CourseModel } from "./courseModel";
import type { ReplyModel } from "./replyModel";

export interface UserModel {
  id: string;
  username: string;
  password: string;
  title?: string;
  name: string;
  surname: string;
  img: string;
  role: "STUDENT" | "TEACHER" | "ADMIN";
  department: string;
  academicPosition?: string;
  advisor?: UserModel;
  advisees?: UserModel[];
  studyCourses?: CourseModel[];
  teachCourses?: CourseModel[];
  comments?: CommentModel[];
  createdComments?: CommentModel[];
  createdReplies?: ReplyModel[];
}
