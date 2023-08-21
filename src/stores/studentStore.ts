import type { StudentModel } from "@/models/studentModel";
import { defineStore } from "pinia";

export const useStudentStore = defineStore('student', {
  state: () => ({
    studentList: [] as StudentModel[],
    student: null as StudentModel | null
  }),
  actions: {
    setStudentList(studentList: StudentModel[]){
      this.studentList = studentList;
    },
    setStudent(student: StudentModel){
      this.student = student
    }
  }
})