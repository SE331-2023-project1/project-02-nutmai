import type { CommentModel } from "@/models/commentModel";
import type { StudentModel } from "@/models/schoolModel";
import { defineStore } from "pinia";

export const useStudentStore = defineStore("student", {
  state: () => ({
    student: null as StudentModel | null,
  }),
  actions: {
    setStudent(student: StudentModel) {
      this.student = student;
    },
  },
  getters: {
    getStudent: (state) => {
      return state.student;
    },
  },
});
