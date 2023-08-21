import type { CourseModel } from "./courseModel";

export interface StudentModel{
  id: string;
  name: string;
  surname: string;
  img: string;
  courses: CourseModel[]
  advisorID: string;
}