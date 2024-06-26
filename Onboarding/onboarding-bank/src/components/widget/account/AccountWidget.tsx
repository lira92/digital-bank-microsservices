"use client";

import AccountService, { Account } from "@/services/account/AccountService";
import VectorHome from "@/vectors/home/VectorHome";
import { useRouter } from "next/navigation";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { useEffect, useState } from "react";
import { Col, Row } from "reactstrap";

export default function AccountWidget() {
  const router = useRouter();
  const [account, setAccount] = useState<Account>({} as Account);

  useEffect(() => {
    const account = window.localStorage.getItem("account");
    if (account) {
      setAccount(JSON.parse(account));
    }
  }, []);

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
              className="d-flex align-items-center justify-content-cente"
            >
              <Card className="p-4">
                <h2>Minha conta</h2>
                <p>
                  <strong>Nome:</strong> {account.nome}
                </p>
                <p>
                  <strong>E-mail:</strong> {account.email}
                </p>
                <p>
                  <strong>Saldo:</strong> R$ {account.saldo}
                </p>
                <Button
                  label="Sair da conta"
                  className="p-button-danger"
                  onClick={() => {
                    window.localStorage.removeItem("account");
                    router.push("/");
                  }}
                />
              </Card>
            </Col>
          </Row>
        </section>
      </div>
    </>
  );
}
