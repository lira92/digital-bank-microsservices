import Application from "@/components/application/Application";
import HomeView from "@/views/home/HomeView";

import type { Metadata } from "next";

const serverURL =
  process.env.NODE_ENV === "development"
    ? "http://localhost:3000"
    : "https://onboarding-bank.vercel.app";

const siteName = "Biobank";
const title = "Biobank - Banco digital do Biopark";
const description = "Boas vindas ao banco digital do Biopark!";

export const metadata: Metadata = {
  title,
  description: "Boas vindas ao banco digital do Biopark!",
  openGraph: {
    type: "website",
    url: serverURL,
    title: title,
    description,
    siteName,
    images: [
      {
        url: `${serverURL}/cover.png`,
      },
    ],
  },
};

export default function Home() {
  return (
    <Application>
      <HomeView />
    </Application>
  );
}
