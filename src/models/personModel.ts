import type { CourseModel } from "./courseModel";

export interface PersonModel {
  id: string;
  title?: string;
  name: string;
  surname: string;
  img: string;
  courses: CourseModel[]
}