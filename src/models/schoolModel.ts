export interface UserModel {
  id: string;
  username: string;
  password: string;
  title?: string;
  name: string;
  surname: string;
  img: string;
  role: 'student' | 'teacher' | 'admin';
  department: string;
}

export interface StudentModel extends UserModel {
  advisor: TeacherModel;
  courses: CourseModel[];
}

export interface TeacherModel extends UserModel {
  academicPosition?: string;
  advisees?: StudentModel[];
  courses: CourseModel[];
}

export interface CourseModel {
  id: string;
  name: string;
  description?: string;
  teacher: TeacherModel;
  students: StudentModel[];
}
