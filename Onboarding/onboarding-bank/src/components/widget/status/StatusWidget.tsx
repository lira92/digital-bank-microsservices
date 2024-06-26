"use client";

import { Prospect } from "@/models/prospect/ProspectModel";
import { useRouter } from "next/navigation";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Toast } from "primereact/toast";
import { useRef, useState } from "react";
import { Col, Row } from "reactstrap";

export default function StatusWidget() {
  const [email, setEmail] = useState("");
  const [result, setResult] = useState<Prospect | null>(null);
  const data = new Date().toLocaleDateString();
  const toast = useRef<Toast>(null);
  const router = useRouter();
  const verify = async () => {
    const response = await fetch(`/api/prospect/status/${email}`);
    const data = await response.json();
    if (data) setResult(data);
    if (!data) {
      toast.current?.show({
        severity: "error",
        summary: "Erro",
        detail: "Nenhum resultado encontrado para o email informado",
        life: 3000,
      });
    }
  };
  return (
    <>
      <div className="bg-light" style={{ minHeight: "calc(100vh - 40px)" }}>
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
        <section className="container p-4">
          <Row>
            <Col>
              <h1>Verificar status da analise</h1>
              <p>
                Verifique o status da sua analise preenchendo o campo abaixo
              </p>
              <div className="d-flex">
                <InputText
                  required
                  className="flex-grow-1 me-1"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="Digite seu email"
                />
                <div className="text-end d-md-block d-none">
                  <Button
                    className="me-1"
                    severity="secondary"
                    label="Voltar"
                    onClick={() => router.push("/")}
                  />
                  <Button label="Verificar análise" onClick={verify} />
                </div>
              </div>
            </Col>
          </Row>
          <Row className="d-md-none d-block mt-4">
            <Col>
              <div className="text-end">
                <Button
                  className="me-1"
                  severity="secondary"
                  label="Voltar"
                  onClick={() => router.push("/")}
                />
                <Button label="Verificar análise" onClick={verify} />
              </div>
            </Col>
          </Row>
        </section>
        {result && (
          <>
            <section>
              <div className="container p-4">
                <Row>
                  <Col>
                    <h2>Resultado encontrado</h2>
                    <p className="text-muted">Dados emitidos em: {data}</p>
                    <p>
                      <strong>Nome:</strong> {result.nome}
                    </p>
                    <p>
                      <strong>Email:</strong> {result.email}
                    </p>
                    <p>
                      <strong>CPF:</strong> {result.documento}
                    </p>
                    <p>
                      <strong>Telefone:</strong> {result.telefone}
                    </p>
                    <p>
                      <strong>Data de nascimento:</strong>{" "}
                      {new Date(result.data_nascimento).toLocaleDateString()}
                    </p>
                    <p>
                      <strong>Status:</strong>{" "}
                      {result.status ? "Aprovado" : "Aguardando aprovação"}
                    </p>
                  </Col>
                </Row>
              </div>
            </section>
          </>
        )}
      </div>
      <Toast ref={toast} position="bottom-center" />
    </>
  );
}
