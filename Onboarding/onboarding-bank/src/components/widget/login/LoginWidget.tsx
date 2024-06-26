"use client";

import AccountService, { Account } from "@/services/account/AccountService";
import VectorHome from "@/vectors/home/VectorHome";
import { useRouter } from "next/navigation";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { InputText } from "primereact/inputtext";
import { Toast } from "primereact/toast";
import { useRef, useState } from "react";
import { Col, Row } from "reactstrap";

export default function LoginWidget() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  const router = useRouter();
  const toast = useRef<Toast>(null);

  const login = async () => {
    const request = await fetch("/api/login", {
      method: "POST",
      body: JSON.stringify({ email, senha }),
    })

    const response = await request.json();
    console.log("response", response);

    if (response.success) {
      const account: Account | null = response.data;
      console.log("account", account);
      if (account) {
        window.localStorage.setItem("account", JSON.stringify(account));
        router.push("/conta");
      } else {
        toast.current?.show({
          severity: "error",
          summary: "Erro",
          detail: "E-mail ou senha inválidos",
        });
      }
    }
  };

  return (
    <>
      <div className="bg-light">
        <section className="container-fluid bg-primary text-white">
          <Row>
            <Col xs={12}>
              <div className="text-center p-5">
                <h1>BIOBANK</h1>
                <p>Boas vindas ao banco digital do Biopark!</p>
              </div>
            </Col>
          </Row>
        </section>
        <section className="container-fluid bg-secondary vh-100 min-vh-100 text-white">
          <Row className="vh-100 min-vh-100">
            <Col
              md={6}
              className="d-flex align-items-center justify-content-center"
            >
              <VectorHome />
            </Col>
            <Col
              md={6}
              className="d-flex align-items-center justify-content-center"
            >
              <Card className="p-4">
                <h1 className="text-center">Acessar minha conta</h1>
                <p className="text-end">Faça login para acessar o Biobank</p>
                <div>
                  <InputText
                    id="email"
                    type="email"
                    placeholder="Insira seu e-mail"
                    className="w-100 mb-3"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <div>
                  <InputText
                    id="senha"
                    type="password"
                    placeholder="Insira sua senha"
                    className="w-100 mb-3"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                  />
                </div>
                <div className="d-flex justify-content-end">
                  <Button
                    severity="secondary"
                    label="Voltar"
                    className="me-1"
                    onClick={() => router.push("/")}
                  />
                  <Button label="Acessar conta" className="" onClick={login} />
                </div>
              </Card>
            </Col>
          </Row>
        </section>
      </div>
      <Toast position="bottom-center" />
    </>
  );
}
