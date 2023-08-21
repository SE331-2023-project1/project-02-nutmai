import { createRouter, createWebHistory } from 'vue-router'
import StudentListView from '../views/StudentListView.vue'
import StudentDetailView from '../views/StudentDetailView.vue'
import { useStudentStore } from '@/stores/studentStore'
import { getStudentById, getStudentsService } from '@/services/studentService'
import nProgress from 'nprogress'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'student-list',
      component: StudentListView,
      props: (route) => ({
        page: parseInt((route.query?.page as string) || "1"),
      }),
    },
    {
      path: '/student-detail/:id',
      name: 'student-detail',
      component: StudentDetailView,
      props: true,
      beforeEnter: (to) => {
        const id = String(to.params.id)
        const studentStore = useStudentStore()
        return getStudentById(id).then(res => studentStore.setStudent(res)).catch((err) => {
          if(err.response && err.response.status === 404){
            return {
              name: '404-resource',
              params: {resource: 'event'}
            }
          }else {
            return {name: 'network-error'}
          }
        })
      }
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    if(savedPosition) {
      return savedPosition
    }else{
      return {top:0}
    }
  },
  linkActiveClass: "text-rose-800"
})

router.beforeEach(() => {
  nProgress.start()
})

router.afterEach(() => {
  nProgress.done()
})

export default router
