"use client";

import { useRef, useState } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { InputMask } from "primereact/inputmask";
import { InputSwitch } from "primereact/inputswitch";
import { Col, Row } from "reactstrap";
import { Card } from "primereact/card";
import { Toast } from "primereact/toast";
import { Prospect } from "@/models/prospect/ProspectModel";
import { useRouter } from "next/navigation";
import { Calendar } from "primereact/calendar";
import { Nullable } from "primereact/ts-helpers";

type TipoPessoa = "fisica" | "juridica";

export default function RegisterWidget() {
  const [nome, setNome] = useState("");
  const [documento, setDocumento] = useState("");
  const [email, setEmail] = useState("");
  const [confimarSenha, setConfirmarSenha] = useState("");
  const [senha, setSenha] = useState("");
  const [tipoPessoa, setTipoPessoa] = useState<TipoPessoa>("fisica");
  const [dataNascimento, setDatanascimento] = useState<Nullable<Date>>(null);
  const [telefone, setTelefone] = useState<string>("");
  const toast = useRef<Toast>(null);
  const [result, setResult] = useState<Prospect | null>(null);
  const router = useRouter();

  const register = async () => {
    const validation = validate();
    if (!validation.success) {
      toast.current?.show({
        severity: "error",
        summary: "Erro",
        detail: validation.message || "Não foi possível realizar o cadastro",
        life: 3000,
      });
      return;
    }

    const response = await fetch("/api/prospect", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nome,
        documento,
        email,
        senha,
        data_nascimento: dataNascimento,
        telefone,
      }),
    });

    const data = await response.json();
    if (data?.id_prospecto) setResult(data);
    console.log(data);
    if (data?.success === false) {
      toast.current?.show({
        severity: "error",
        summary: "Erro",
        detail: data?.message || "Não foi possível realizar o cadastro",
        life: 3000,
      });
    } else {
      toast.current?.show({
        severity: "success",
        summary: "Sucesso",
        detail: "Cadastro realizado com sucesso",
        life: 3000,
      });
      new Promise((resolve) => setTimeout(resolve, 3000)).then(() => {
        router.push(`/`);
      });
    }
  };

  const validate = () => {
    let result = {
      message: "",
      success: true,
    };
    if (!nome || nome.length < 3) {
      result.message = "Nome é obrigatório";
      result.success = false;
    }
    if (!documento || documento.length < 11) {
      result.message =
        "Documento é obrigatório e deve ter no mínimo 11 caracteres";
      result.success = false;
    }
    if (!email || email.length < 5) {
      result.message = "Email é obrigatório e deve ter no mínimo 5 caracteres";
      result.success = false;
    }
    if (!senha || senha.length < 6) {
      result.message = "Senha é obrigatório e deve ter no mínimo 6 caracteres";
      result.success = false;
    }
    if (senha !== confimarSenha) {
      result.message = "Senhas não conferem";
      result.success = false;
    }
    return result;
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
        <section className="p-4">
          <div className="container">
            <h1 className="text-center text-md-start">Abra sua conta</h1>
            <p className="text-center text-md-start text-muted">
              Preencha os campos abaixo para criar sua conta
            </p>
          </div>
        </section>
        <section className="container pb-5">
          <Row>
            <Col xs={12} md={6} className="mb-2">
              <Card>
                <div>
                  <h4>Tipo de pessoa</h4>
                  <p className="text-muted small">Selecione o tipo de pessoa</p>
                  <div className="d-flex">
                    <div className="d-flex m-auto flex-column">
                      <div className="d-flex flex-grow-1">
                        <InputSwitch
                          checked={tipoPessoa === "juridica"}
                          onChange={(e) => {
                            setTipoPessoa(e.value ? "juridica" : "fisica");
                            setDocumento("");
                          }}
                        />
                      </div>
                      <div className="d-flex flex-grow-1">Juridica</div>
                    </div>
                    <div className="d-flex m-auto flex-column">
                      <div className="d-flex flex-grow-1">
                        <InputSwitch
                          checked={tipoPessoa === "fisica"}
                          onChange={(e) => {
                            setTipoPessoa(e.value ? "fisica" : "juridica");
                            setDocumento("");
                          }}
                        />
                      </div>
                      <div className="d-flex flex-grow-1">Fisica</div>
                    </div>
                  </div>
                </div>
              </Card>
            </Col>
            <Col xs={12} md={6} className="mb-2">
              <Card>
                <h2>Já tenho conta</h2>
                <p>Já possui uma conta? clique no botão abaixo para acessar</p>
                <Button
                  className="w-100"
                  label="Acessar minha conta"
                  onClick={() => router.push("/login")}
                  />
              </Card>
            </Col>
            <Col xs={12} className="mb-2 mb-md-auto">
              <div>
                <h4>Nome completo</h4>
                <p className="text-muted small">
                  Insira seu nome completo assim como consta em seu documento
                </p>
                <InputText
                  className="w-100"
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  id="nome"
                  placeholder="Nome completo"
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Documento</h4>
                <p className="text-muted small">Insira seu CPF ou CNPJ</p>
                <InputMask
                  className="w-100"
                  mask={
                    tipoPessoa === "fisica"
                      ? "999.999.999-99"
                      : "99.999.999/9999-99"
                  }
                  value={documento}
                  onChange={(e) => setDocumento(e.target.value || "")}
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Endereço de email</h4>
                <p className="text-muted small">Insira um email válido</p>
                <InputText
                  className="w-100"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  id="email"
                  type="email"
                  placeholder="Email"
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Data de nascimento</h4>
                <p className="text-muted small">
                  Insira sua data de nascimento
                </p>
                <Calendar
                  inputClassName="w-100"
                  className="w-100"
                  value={dataNascimento}
                  onChange={(e) => setDatanascimento(e.value)}
                  placeholder="Data de nascimento"
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Telefone de contato</h4>
                <p className="text-muted small">
                  Insira um telefone de contato
                </p>
                <InputMask
                  className="w-100"
                  mask={telefone.length < 15 ? "(99) 9999-9999" : "(99) 99999-9999"}
                  value={telefone}
                  onChange={(e: any) => setTelefone(e.target.value)}
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Senha de acesso</h4>
                <p className="text-muted small">Insira uma senha de acesso</p>
                <InputText
                  type="password"
                  className="w-100"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                  id="senha"
                  placeholder="Insira sua senha"
                />
              </div>
            </Col>
            <Col xs={12} md={6} className="mb-2 mb-md-auto">
              <div>
                <h4>Confirmar senha</h4>
                <p className="text-muted small">Confirme a senha de acesso</p>
                <InputText
                  type="password"
                  className="w-100"
                  value={confimarSenha}
                  onChange={(e) => setConfirmarSenha(e.target.value)}
                  id="confirmar-senha"
                  placeholder="Confirme sua senha"
                />
              </div>
            </Col>
          </Row>
          <hr className="border-gray" />
          <Card className="mt-2 mb-4">
            <Row>
              <Col>
                <div>
                  <h4>Muita atenção!</h4>
                  <p className="text-muted">
                    Seu cadastro está sujeito a analise de crédito e aprovação
                    de abertura de conta.
                  </p>
                </div>
              </Col>
            </Row>
          </Card>
          <Row>
            <Col>
              <div className="d-flex justify-content-end">
                <Button
                  severity="secondary"
                  className="me-1"
                  label="Voltar"
                  onClick={() => router.push("/")}
                />
                <Button label="Abrir minha conta" onClick={register} />
              </div>
            </Col>
          </Row>
        </section>
      </div>
      <Toast ref={toast} position="bottom-center" />
    </>
  );
}
