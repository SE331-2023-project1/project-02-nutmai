import type { UserModel } from "./userModel";

export interface CourseModel {
  id: string;
  name: string;
  description?: string;
  teacher: UserModel;
  students: UserModel[];
}