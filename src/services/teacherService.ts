import type { TeacherModel } from "@/models/schoolModel";
import axiosClient from "@/utils/axiosClient";

export async function getTeachersService(perPage?: number, page?: number) {
    if (perPage && page)
        return axiosClient.get<TeacherModel[]>("/teachers?_limit=" + perPage + "&_page=" + page).then((res) => {
            return { data: res.data, total: res.headers["x-total-count"] };
        });
    return axiosClient.get<TeacherModel[]>("/teachers").then((res) => res.data);
}

export async function getTeacherById(id: string) {
    return axiosClient.get<TeacherModel>('/teachers/' + id).then(res => res.data)
} 