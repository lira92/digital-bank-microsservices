"use client";

import VectorHome from "@/vectors/home/VectorHome";
import { useRouter } from "next/navigation";
import { Button } from "primereact/button";
import { Card } from "primereact/card";
import { Col, Row } from "reactstrap";

export default function HomeWidget() {
  const router = useRouter();
  return (
    <>
      <div className="bg-light">
        <section className="container-fluid bg-white">
          <Row>
            <Col></Col>
            <Col xs={12} md>
              <div className="text-center p-5">
                <h1>BIOBANK</h1>
                <p>Boas vindas ao banco digital do Biopark!</p>
              </div>
            </Col>
            <Col className="my-auto pb-5 pb-md-0">
              {/** <Col className="my-auto pb-5 pb-md-0"> */}
              <Row>
                <Col xs="auto" className="ms-auto">
                <Button label="Acessar conta" onClick={()=>{
                  router.push("acessar")
                }} />
                </Col>
              </Row>
            </Col>
          </Row>
        </section>
        <section className="container-fluid bg-primary mb-5">
          <div className="text-center p-5">
          <VectorHome />
          </div>
        </section>
        <section className="container mb-5">
          <Row>
            <Col xs={12} md={6} className="mb-4 mb-md-0">
              <Card>
                <h2>Abertura de conta</h2>
                <p>Para abrir uma conta, clique no botão abaixo.</p>
                <div className="text-end">
                  <Button
                    label="Abrir minha conta"
                    onClick={() => router.push("abrir-conta")}
                  />
                </div>
              </Card>
            </Col>
            <Col xs={12} md={6}>
              <Card>
                <h2>Status da analise</h2>
                <p>Verifique o status da sua analise.</p>
                <div className="text-end">
                  <Button
                    label="Verificar status"
                    onClick={() => router.push("status-analise")}
                  />
                </div>
              </Card>
            </Col>
          </Row>
        </section>
      </div>
    </>
  );
}
