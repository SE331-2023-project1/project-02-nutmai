import { useCommentStore } from "./commentStore";
import { useStudentStore } from "./studentStore";
import { useTeacherStore } from "./teacherStore";

export * from "./commentStore";
export * from "./studentStore";
export * from "./teacherStore";

export const useStore = () => {
  return {
    commentStore: useCommentStore(),
    studentStore: useStudentStore(),
    teacherStore: useTeacherStore(),
  };
}