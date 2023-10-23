import type { CommentModel } from "@/models/commentModel";
import type { UserModel } from "@/models/userModel";
import { defineStore } from "pinia";

export const useStudentStore = defineStore("student", {
  state: () => ({
    student: null as UserModel | null,
  }),
  actions: {
    setStudent(student: UserModel) {
      this.student = student;
    },
  },
  getters: {
    getStudent: (state) => {
      return state.student;
    },
  },
});
