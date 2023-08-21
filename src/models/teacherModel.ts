import type { CourseModel } from "./courseModel";

export interface TeacherModel {
  id: string;
  name: string;
  surname: string;
  img: string;
  courses: CourseModel[]
}