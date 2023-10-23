import type Pagination from "@/models/Pagination";
import type { UserModel } from "@/models/userModel";
import axiosClient from "@/utils/axiosClient";

export async function getTeachersService(perPage?: number, page?: number) {
    if (perPage && page)
        return axiosClient.get<Pagination<UserModel>>("/users/getTeachers?page=" + (page - 1) + "&pageSize=" + perPage).then((res) => {
            return res.data;
        });
    return axiosClient.get<Pagination<UserModel>>("/users/getTeachers?page=0&pageSize=1000").then((res) => res.data);
}

export async function getTeacherById(id: string) {
    return axiosClient.get<UserModel>('/users/getUserById?id=' + id).then(res => res.data)
} 