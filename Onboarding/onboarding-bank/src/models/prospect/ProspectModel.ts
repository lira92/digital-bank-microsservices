import { PrismaClient, Prospecto } from "@prisma/client";
const prisma = new PrismaClient();

export type Prospect = Prospecto;
export type ProspectWithoutId = Omit<Prospecto, "id_prospecto">;

export default class ProspectModel {
  static async getAll() {
    return prisma.prospecto.findMany();
  }

  static async getById(id_prospecto: number) {
    return prisma.prospecto.findFirst({
      where: {
        id_prospecto,
      },
    });
  }

  static async getByEmail(email: string) {
    return prisma.prospecto.findFirst({
      where: {
        email,
      },
    });
  }

  static async create(prospect: ProspectWithoutId) {
    return prisma.prospecto.create({
      data: prospect,
    });
  }

  static async update(id_prospecto: number, prospect: Prospect) {
    return prisma.prospecto.update({
      where: {
        id_prospecto,
      },
      data: prospect,
    });
  }

  static async delete(id_prospecto: number) {
    return prisma.prospecto.delete({
      where: {
        id_prospecto,
      },
    });
  }
}
