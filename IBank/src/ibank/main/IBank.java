package ibank.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import ibank.dominio.Conta;
import ibank.dominio.Genero;
import ibank.negocio.TransacaoHelper;
import ibank.negocio.ValidacaoHelper;

public class IBank {

	public static void main(String[] args) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		TransacaoHelper transacaoHelper = new TransacaoHelper();

		List<Conta> contas = new ArrayList<Conta>();

		String opcao = "";

		boolean sair = false;

		Scanner teclado = new Scanner(System.in);

		System.out.println("""
				*------------------------------------------------------------*
				|                    Bem-vindo ao IBank!                     |
				*------------------------------------------------------------*
				""");

		try {
			do {
				System.out.println("""
				*------------------------------------------------------------*
				|                       Menu de Opções                       |
				|  0 - Sair                                                  |
				|  1 - Criar Conta                                           |
				|  2 - Listar Todas as Contas                                |
				|  3 - Exibir Dados Bancários pelo CPF                       |
				|  4 - Visualizar Saldo Pelo CPF                             |
				|  5 - Solicitar Cartão de Crédito                           |
				|  6 - Sacar                                                 |
				|  7 - Depositar                                             |
				|  8 - Transferir                                            |
				*------------------------------------------------------------*

				Selecione uma opção:
				""");
				opcao = teclado.nextLine();

				switch (opcao) {
					case "0":
						System.out.println("""
						*------------------------------------------------------------*
						|                 Encerrando a Aplicação...                  |
						|                 Agradecemos sua visita!                    |
						*------------------------------------------------------------*
						""");
						sair = true;
						break;
					case "1":
						System.out.println("-> Criar Nova Conta");

						Conta c1 = new Conta();
						System.out.print("Por favor, informe o nome do titular: ");
						c1.getPessoa().setNome(teclado.nextLine());

						System.out.print("Por favor, informe o CPF do titular: ");
						c1.getPessoa().setCpf(teclado.nextLine());

						System.out.print("Informe o gênero do titular (Masculino ou Feminino): ");
						c1.getPessoa().setGenero(Genero.modificarStringToGenero(teclado.nextLine()));

						System.out.print("Informe a data de nascimento do titular (dd/MM/yyyy): ");
						String dataNascimento = teclado.nextLine();

						if (!dataNascimento.isEmpty()) {
							c1.getPessoa().setDataNascimento(simpleDateFormat.parse(dataNascimento));
						}

						if (ValidacaoHelper.isPossivelCadastrarConta(c1)) {
							contas.add(c1);
							System.out.println("🎉 Conta criada com sucesso! 🎉");
						} else {
							System.out.println("❌ Não foi possível criar a conta. Verifique os dados e tente novamente.");
						}
						break;
					case "2":
						System.out.println("-> Listar Todas as Contas");

						if (!contas.isEmpty()) {
							for (Conta conta : contas) {
								System.out.printf("Número da Conta: %s | Agência: %s%n", conta.getNumero(), conta.getCodigo());
							}
						} else {
							System.out.println("🔍 Nenhuma conta cadastrada até o momento.");
						}
						break;
					case "3":
						System.out.println("-> Exibir Dados Bancários");

						System.out.print("Por favor, informe o CPF do titular: ");
						String cpfDados = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfDados)) {
							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfDados)) {
									transacaoHelper.exibirDadosBancarios(conta);
								}
							}
						} else {
							System.out.println("❌ Conta não encontrada para o CPF informado.");
						}
						break;
					case "4":
						System.out.println("-> Visualizar Saldo");

						System.out.print("Por favor, informe o CPF do titular: ");
						String cpfSaldo = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfSaldo)) {
							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfSaldo)) {
									System.out.printf("💰 O saldo disponível é: %.2f%n", conta.getSaldo());
								}
							}
						} else {
							System.out.println("❌ Conta não encontrada para o CPF informado.");
						}
						break;
					case "5":
						System.out.println("-> Solicitar Cartão de Crédito");

						System.out.print("Por favor, informe o CPF do titular: ");
						String cpfCartao = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfCartao)) {
							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfCartao)) {
									transacaoHelper.solicitarCartao(conta);
								}
							}
						} else {
							System.out.println("❌ Conta não encontrada para o CPF informado.");
						}
						break;
					case "6":
						System.out.println("-> Realizar Saque");

						System.out.print("Por favor, informe o CPF do titular: ");
						String cpfSacar = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfSacar)) {
							String valor;
							System.out.print("Informe o valor do saque: ");
							valor = teclado.nextLine();

							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfSacar)) {
									transacaoHelper.sacar(conta, Double.parseDouble(valor));
								}
							}
						} else {
							System.out.println("❌ Conta não encontrada para o CPF informado.");
						}
						break;
					case "7":
						System.out.println("-> Realizar Depósito");

						System.out.print("Por favor, informe o CPF do titular: ");
						String cpfDeposito = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfDeposito)) {
							String valor;
							System.out.print("Informe o valor do depósito: ");
							valor = teclado.nextLine();

							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfDeposito)) {
									transacaoHelper.depositar(conta, Double.parseDouble(valor));
								}
							}
						} else {
							System.out.println("❌ Conta não encontrada para o CPF informado.");
						}
						break;
					case "8":
						System.out.println("-> Realizar Transferência");

						System.out.print("Por favor, informe o CPF do depositante: ");
						String cpfDepositante = teclado.nextLine();

						System.out.print("Por favor, informe o CPF do recebedor: ");
						String cpfRecebedor = teclado.nextLine();

						if (ValidacaoHelper.isContaExistente(contas, cpfDepositante) && ValidacaoHelper.isContaExistente(contas, cpfRecebedor)) {
							Conta contaDepositante = null, contaRecebedor = null;
							String valor;

							System.out.print("Informe o valor da transferência: ");
							valor = teclado.nextLine();

							for (Conta conta : contas) {
								if (conta.getPessoa().getCpf().equals(cpfDepositante)) {
									contaDepositante = conta;
								} else if (conta.getPessoa().getCpf().equals(cpfRecebedor)) {
									contaRecebedor = conta;
								}
							}

							transacaoHelper.transferir(contaDepositante, contaRecebedor, Double.parseDouble(valor));
						} else {
							System.out.println("❌ Uma ou ambas as contas não foram encontradas.");
						}
						break;
					default:
						System.out.println("⚠️ Opção inválida. Por favor, tente novamente.");
						break;
				}
			} while (!sair);

		} catch (ParseException e) {
			System.err.println("❌ Erro ao processar a data. Verifique o formato (dd/MM/yyyy).");
			e.printStackTrace();
		} finally {
			teclado.close();
		}
	}
}
