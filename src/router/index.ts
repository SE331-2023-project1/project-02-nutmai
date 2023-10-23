import { createRouter, createWebHistory } from "vue-router";
import { useStudentStore } from "@/stores/studentStore";
import { getStudentById } from "@/services/studentService";
import nProgress from "nprogress";
import { useTeacherStore } from "@/stores/teacherStore";
import { getTeacherById } from "@/services/teacherService";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
    },
    {
      path: "/student-list",
      name: "student-list",
      component: () => import("@/views/StudentListView.vue"),
      props: (route) => ({
        page: parseInt((route.query?.page as string) || "1"),
      }),
    },
    {
      path: "/student-detail/:id",
      name: "student-detail",
      component: () => import("@/views/StudentDetailView.vue"),
      props: true,
      beforeEnter: async (to) => {
        const id = String(to.params.id);
        const studentStore = useStudentStore();
        const teacherStore = useTeacherStore();
        try{
          const student = await getStudentById(id)
          studentStore.setStudent(student);
          const teacher = await getTeacherById(student.advisor?.id || '');
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
      path: "/teacher-list",
      name: "teacher-list",
      component: () => import("@/views/TeacherListView.vue"),
      props: (route) => ({
        page: parseInt((route.query?.page as string) || "1"),
      }),
    },
    {
      path: '/user-profile',
      name: 'user-profile',
      component: () => import('@/views/UserProfileView.vue'),
    },
    {
      path: '/advisee-list',
      name: 'advisee-list',
      component: () => import('@/views/AdviseeListView.vue'),
    },
    {
      path: "/admin",
      name: "admin",
      component: () => import("@/views/AdminView.vue"),
      beforeEnter: async (to) => {
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        const isAuthenticated = user.token;
        if(isAuthenticated){
          if(user.role !== 'ADMIN'){
            return {
              name: 'student-list'
            }
          }
        }
      }
    },
    {
      path: '/404',
      name: 'not-found',
      component: () => import('@/views/NotFoundView.vue'),
    },
    {
      path: '/network-error',
      name: 'network-error',
      component: () => import('@/views/NetworkErrorView.vue'),
    }
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

router.beforeEach(async (to, from) => {
  nProgress.start();
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const isAuthenticated = user.token;
  if(!isAuthenticated){
    if(to.name !== 'login' && to.name !== 'register'){
      return {
        name: 'login'
      }
    }
  }
  if(isAuthenticated){
    if(to.name === 'login' || to.name === 'register'){
      return {
        name: 'student-list'
      }
    }
  }
});

router.afterEach(() => {
  nProgress.done();
});

export default router;
