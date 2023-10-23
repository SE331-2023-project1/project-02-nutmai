import { useCommentStore } from "./commentStore";
import { useStudentStore } from "./studentStore";
import { useTeacherStore } from "./teacherStore";
import { useUserStore } from "./userStore";

export * from "./commentStore";
export * from "./studentStore";
export * from "./teacherStore";
export * from "./userStore";

export const useStore = () => {
  return {
    commentStore: useCommentStore(),
    studentStore: useStudentStore(),
    teacherStore: useTeacherStore(),
    userStore: useUserStore(),
  };
}