import "@/styles/globals.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import "primeicons/primeicons.css";
import "primereact/resources/themes/lara-light-blue/theme.css";
import FooterLayout from "../layout/footer/FooterLayout";

export default function Application({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <main className="vh-100">
      <div>{children}</div>
      <FooterLayout/>
    </main>
  );
}
