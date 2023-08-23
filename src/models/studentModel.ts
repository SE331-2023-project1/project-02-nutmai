import type { CourseModel } from "./courseModel";
import type { PersonModel } from "./personModel";

export interface StudentModel extends PersonModel{
  advisorID: string;
}