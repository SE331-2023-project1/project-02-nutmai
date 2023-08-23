import type { TeacherModel } from "./teacherModel";

export interface CommentModel{
  id: string;
  studentID: string;
  teacher: TeacherModel
  content: string;
  date: Date;
}