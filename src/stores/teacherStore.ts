import type { UserModel } from "@/models/userModel";
import { defineStore } from "pinia";

export const useTeacherStore = defineStore("teacher", {
  state: () => ({
    teacher: {} as UserModel,
  }),
  actions: {
    setTeacher(teacher: UserModel) {
      this.teacher = teacher;
    },
  },
});