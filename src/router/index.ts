import { createRouter, createWebHistory } from "vue-router";
import StudentListView from "../views/StudentListView.vue";
import StudentDetailView from "../views/StudentDetailView.vue";
import TeacherListView from "../views/TeacherListView.vue";
import { useStudentStore } from "@/stores/studentStore";
import { getStudentById, getStudentsService } from "@/services/studentService";
import nProgress from "nprogress";
import { useTeacherStore } from "@/stores/teacherStore";
import { getTeacherById } from "@/services/teacherService";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "student-list",
      component: StudentListView,
      props: (route) => ({
        page: parseInt((route.query?.page as string) || "1"),
      }),
    },
    {
      path: "/student-detail/:id",
      name: "student-detail",
      component: StudentDetailView,
      props: true,
      beforeEnter: async (to) => {
        const id = String(to.params.id);
        const studentStore = useStudentStore();
        const teacherStore = useTeacherStore();
        try{
          const student = await getStudentById(id)
          studentStore.setStudent(student);
          const teacher = await getTeacherById(student.advisorID);
          teacherStore.setTeacher(teacher);
        }catch(err:any){
          if (err.response && err.response.status === 404) {
            return {
              name: "404-resource",
              params: { resource: "event" },
            };
          } else {
            return { name: "network-error" };
          }
        }
      },
    },
    {
      path: "/",
      name: "teacher-list",
      component: TeacherListView,
      props: (route) => ({
        page: parseInt((route.query?.page as string) || "1"),
      }),
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
  linkActiveClass: "text-rose-500",
});

router.beforeEach(() => {
  nProgress.start();
});

router.afterEach(() => {
  nProgress.done();
});

export default router;
