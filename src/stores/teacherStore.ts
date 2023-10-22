import type { TeacherModel } from "@/models/schoolModel";
import { defineStore } from "pinia";

export const useTeacherStore = defineStore("teacher", {
  state: () => ({
    teacher: {} as TeacherModel,
  }),
  actions: {
    setTeacher(teacher: TeacherModel) {
      this.teacher = teacher;
    },
  },
});