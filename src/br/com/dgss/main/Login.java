package br.com.dgss.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6332548249273725169L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private WebDriver driver = null;
	private JPasswordField txtSenha;
	private boolean erroNoLogin;
	private JLabel lblCaminhoArquivo;
	private JTextField txtCaminhoArquivos;
	private static JButton btnLogar;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HHmmss");
	private JRadioButton rdoVirtual;
	private JRadioButton rdoReal;
	private JTextField txtIdConta;
	private JSpinner spinner_1;
	private JSpinner spinner;
	private JSpinner.DateEditor de1;
	private JSpinner.DateEditor de2;
	private Date spinnerInicio = null, spinnerFim = null;
	private Timer timerComprarVender;
	private JComboBox<?> comboMoeda;
	private JCheckBox chckbxPermitirIgualdade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.getRootPane().setDefaultButton(btnLogar);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Fast BO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 364, 250);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(10, 14, 46, 14);
		contentPane.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 42, 46, 14);
		contentPane.add(lblSenha);

		txtLogin = new JTextField();
		txtLogin.setBounds(48, 11, 203, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setText("douglas.santosreis@gmail.com");

		btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (validarLogin()) {
					iniciarAutomacaoBinary();
				}
			}
		});

		btnLogar.setBounds(151, 181, 89, 23);
		contentPane.add(btnLogar);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(48, 39, 203, 20);

		txtSenha.setText("Douglas_03041992");
		contentPane.add(txtSenha);

		lblCaminhoArquivo = new JLabel("Caminho dos arquivos:");
		lblCaminhoArquivo.setBounds(10, 137, 188, 14);
		contentPane.add(lblCaminhoArquivo);

		txtCaminhoArquivos = new JTextField();
		txtCaminhoArquivos.setBounds(10, 152, 329, 20);
		contentPane.add(txtCaminhoArquivos);
		txtCaminhoArquivos.setColumns(10);
		txtCaminhoArquivos.setText("C:\\binary");

		rdoVirtual = new JRadioButton("Conta Virtual");
		rdoVirtual.setBounds(89, 63, 89, 23);
		rdoVirtual.setSelected(true);
		contentPane.add(rdoVirtual);

		rdoReal = new JRadioButton("Conta Real");
		rdoReal.setBounds(10, 63, 108, 23);
		contentPane.add(rdoReal);

		ButtonGroup grupoBotoes = new ButtonGroup();
		grupoBotoes.add(rdoReal);
		grupoBotoes.add(rdoVirtual);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(250, 181, 89, 23);
		btnParar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				timerComprarVender.cancel();
				btnLogar.setEnabled(true);
			}
		});
		contentPane.add(btnParar);

		txtIdConta = new JTextField();
		txtIdConta.setBounds(10, 106, 172, 20);
		contentPane.add(txtIdConta);
		txtIdConta.setColumns(10);
		txtIdConta.setText("VRTC1334938");

		JLabel lblIdDaConta = new JLabel("ID da conta:");
		lblIdDaConta.setBounds(10, 91, 106, 14);
		contentPane.add(lblIdDaConta);

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);

		SpinnerDateModel sm1 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
		spinner_1 = new JSpinner(sm1);
		spinner_1.setBounds(192, 64, 59, 20);
		contentPane.add(spinner_1);
		de1 = new JSpinner.DateEditor(spinner_1, "HH:mm");
		spinner_1.setEditor(de1);
		contentPane.add(spinner_1, BorderLayout.NORTH);

		SpinnerDateModel sm2 = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm2);
		spinner.setBounds(261, 64, 54, 20);
		contentPane.add(spinner);
		de2 = new JSpinner.DateEditor(spinner, "HH:mm");
		spinner.setEditor(de2);
		contentPane.add(spinner, BorderLayout.NORTH);

		comboMoeda = new JComboBox(new Object[] { "AUD", "EUR", "GBP", "USD", "BCH", "BTC", "DAI", "ETH", "LTC" });
		comboMoeda.setSelectedIndex(3);
		comboMoeda.setBounds(192, 106, 123, 20);
		contentPane.add(comboMoeda);

		chckbxPermitirIgualdade = new JCheckBox("Permitir Igualdade");
		chckbxPermitirIgualdade.setBounds(10, 181, 135, 23);
		contentPane.add(chckbxPermitirIgualdade);
		setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { txtLogin, txtSenha, txtCaminhoArquivos, rdoVirtual, rdoReal,
						txtIdConta, spinner_1, spinner, comboMoeda, chckbxPermitirIgualdade, btnLogar, btnParar }));

		de1.getTextField().setEnabled(true);
		de1.getTextField().setEditable(false);

		de2.getTextField().setEnabled(true);
		de2.getTextField().setEditable(false);

	}

	private boolean validarLogin() {
		boolean validado = false;

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			spinnerInicio = sdf.parse(de1.getFormat().format(spinner_1.getValue()));
			spinnerFim = sdf.parse(de2.getFormat().format(spinner.getValue()));

		} catch (ParseException e) {
		}

		if (txtLogin.getText().isEmpty() || txtSenha.getPassword().toString().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por gentileza, informe o login/senha");
		} else if (txtCaminhoArquivos.getText().isEmpty() && Files.isDirectory(Paths.get("directory"))) {
			JOptionPane.showMessageDialog(null, "Por gentileza, informe o caminho dos arquivos");
		} else if (!Files.isDirectory(Paths.get(txtCaminhoArquivos.getText()))) {
			JOptionPane.showMessageDialog(null, "Por gentileza, informe um caminho correto");
		} else if (!rdoVirtual.isSelected() && !rdoReal.isSelected()) {
			JOptionPane.showMessageDialog(null, "Por gentileza, informe o tipo da conta");
		} else if (txtIdConta.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por gentileza, informe o id da conta");
		} else if ((spinnerInicio != null && spinnerFim != null) && spinnerFim.compareTo(spinnerInicio) < 0) {
			JOptionPane.showMessageDialog(null, "Por gentileza, selecione o horario fim maior que o inicio");
		} else if (comboMoeda.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "Por gentileza, selecione o horario fim maior que o inicio");
		} else {
			validado = true;
		}

		return validado;
	}

	public void criarDiretorioArquivo() {

		File caminho = new File(txtCaminhoArquivos.getText());

		if (!caminho.exists()) {
			caminho.mkdirs();
		}
	}

	private void manipularPutCallBinary() {
		File caminho = new File(txtCaminhoArquivos.getText());

		TimerTask comprarVender = new TimerTask() {
			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

				Date hoje = null;

				try {
					hoje = sdf.parse(sdf.format(new Date()));
				} catch (ParseException e1) {
				}

				if (hoje != null && (hoje.compareTo(spinnerInicio) >= 0 && hoje.compareTo(spinnerFim) <= 0)) {

					try {
						List<File> arquivosNaPasta = new ArrayList<File>();
						File dir = new File(caminho.getAbsolutePath());
						for (File file : dir.listFiles()) {
							if (!file.isDirectory() && file.getName().endsWith((".txt"))) {
								arquivosNaPasta
										.add(new File(caminho.getAbsolutePath().concat("\\").concat(file.getName())));
							}
						}

						if (arquivosNaPasta != null && !arquivosNaPasta.isEmpty()) {

							File caminhoOld = new File(caminho.getAbsolutePath().concat("/old"));
							File caminhoErro = new File(caminho.getAbsolutePath().concat("/erro"));

							if (!caminhoOld.exists()) {
								caminhoOld.mkdirs();
							}

							if (!caminhoErro.exists()) {
								caminhoErro.mkdirs();
							}

							File origem = new File(arquivosNaPasta.get(0).getAbsolutePath());

							String[] entradas = origem.getName().split("_");

							if (entradas.length == 6) {

								try {

									WebElement divMercado = driver.findElement(By.id("contract_markets_container"));
									divMercado.click();

									List<WebElement> moedas = driver.findElements(By.className("symbol_name"));

									for (WebElement webElement : moedas) {
										if (entradas[1].equalsIgnoreCase(webElement.getAttribute("id"))) {
											WebElement mercadoDeCambio = driver.findElement(By.className("forex"));
											mercadoDeCambio.click();
											webElement.click();
											break;
										}
									}

									ClearAndSetText(By.id("duration_amount"), entradas[2]);

									if (rdoReal.isSelected()) {
										List<WebElement> selects = driver.findElements(By.className("select"));
										selects.get(7).click();

										List<WebElement> options = selects.get(7).findElements(By.tagName("li"));
										for (WebElement option : options) {
											if (option.getText().equals(comboMoeda.getSelectedItem().toString())) {
												option.click();
												break;
											}
										}
									}

									WebElement equals_row = driver.findElement(By.id("equals_row"));
									if (chckbxPermitirIgualdade.isSelected() && equals_row.isDisplayed()) {
										try {
											WebElement checkIgualdade = driver.findElement(By.id("callputequal"));
											if (!checkIgualdade.isSelected()) {
												WebElement tooltipIgualdade = driver.findElement(By.id("equals_row"));
												WebElement subTooltipIgualdade = tooltipIgualdade
														.findElement(By.tagName("label"));
												subTooltipIgualdade.findElement(By.tagName("span")).click();
											}
										} catch (Exception e) {
										}
									}

									ClearAndSetText(By.id("duration_units"), entradas[3]);

									ClearAndSetText(By.id("amount"), entradas[4]);

									List<WebElement> button_disabled = driver
											.findElements(By.className("button-disabled"));

									if (button_disabled != null && button_disabled.size() == 0) {
										
										entradas[5] = entradas[5].replace(".txt", "");
										if (entradas[5].equalsIgnoreCase("P")) {
											WebElement btnVenda = driver.findElement(By.id("purchase_button_bottom"));
											btnVenda.click();
										} else if (entradas[5].equalsIgnoreCase("C")) {
											WebElement btnCompra = driver.findElement(By.id("purchase_button_top"));
											btnCompra.click();
										}
										
										File destino = new File(caminhoOld.getAbsolutePath().concat("\\")
												.concat(DATE_FORMAT.format(new Date())).concat(" - ")
												.concat(origem.getName()));
										transferirArquivoPosExecucao(arquivosNaPasta.get(0).getAbsolutePath(), destino);
										origem.delete();
									}else{
										
										File destino = new File(caminhoErro.getAbsolutePath().concat("\\")
												.concat(DATE_FORMAT.format(new Date())).concat(" - ")
												.concat(origem.getName()));
										transferirArquivoPosExecucao(arquivosNaPasta.get(0).getAbsolutePath(), destino);
										origem.delete();
										
										JOptionPane.showMessageDialog(null, "Negociação desabilitada");
										// Manda Email
									}
									

								} catch (Exception e) {
									File destino = new File(caminhoErro.getAbsolutePath().concat("\\")
											.concat(DATE_FORMAT.format(new Date())).concat(" - ")
											.concat(origem.getName()));
									transferirArquivoPosExecucao(arquivosNaPasta.get(0).getAbsolutePath(), destino);
									origem.delete();
									e.printStackTrace();
								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}
		};
		timerComprarVender = new Timer();
		timerComprarVender.schedule(comprarVender, 0l, 1000l);

	}

	private void manterLogado(String id) {
		TimerTask manterOnline = new TimerTask() {
			public void run() {
				try {
					WebElement refreshBalance = driver.findElement(By.id(id));
					refreshBalance.click();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			};
		};

		Timer timerManterOnline = new Timer();
		timerManterOnline.schedule(manterOnline, 0l, 300000l);
	}

	private void transferirArquivoPosExecucao(String pathArquivo, File destino) throws IOException {
		File origem = new File(pathArquivo);
		origem.setWritable(true);
		origem.setReadable(true);
		destino.setWritable(true);
		destino.setReadable(true);

		FileInputStream fisOrigem = new FileInputStream(origem);
		FileOutputStream fisDestino = new FileOutputStream(destino);
		FileChannel fcOrigem = fisOrigem.getChannel();
		FileChannel fcDestino = fisDestino.getChannel();
		fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
		fisOrigem.close();
		fisDestino.close();
	}

	private void clickLinkByHref(String href, WebDriver driver) {
		try {
			List<WebElement> anchors = driver.findElements(By.tagName("a"));
			Iterator<WebElement> i = anchors.iterator();

			while (i.hasNext()) {
				WebElement anchor = i.next();
				if (anchor != null && anchor.getAttribute("href") != null
						&& anchor.getAttribute("href").contains(href)) {
					anchor.click();
					break;
				}
			}
		} catch (Exception e) {

		}
	}

	public void ClearAndSetText(By by, String text) {
		WebElement element = driver.findElement(by);
		Actions navigator = new Actions(driver);
		navigator.click(element).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT)
				.sendKeys(Keys.BACK_SPACE).sendKeys(text).perform();
	}

	private void iniciarAutomacaoBinary() {
		erroNoLogin = false;
		try {
			System.setProperty("webdriver.chrome.driver", "C:/selenium/chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.binary.com/pt/home.html");

			WebElement btnEntrar = driver.findElement(By.id("btn_login"));
			btnEntrar.click();

			WebElement userName = driver.findElement(By.id("txtEmail"));
			userName.sendKeys(txtLogin.getText());

			WebElement password = driver.findElement(By.id("txtPass"));
			password.sendKeys(String.valueOf(txtSenha.getPassword()));

			WebElement botaoLogin = driver.findElement(By.name("login"));
			botaoLogin.click();

			Thread.sleep(5000);
			if (rdoVirtual.isSelected()) {
				WebElement menuContas = driver.findElement(By.className("main-account"));
				menuContas.click();
				WebElement todasContas = driver.findElement(By.id("all-accounts"));
				WebElement li = todasContas.findElement(By.tagName("li"));
				WebElement ul = li.findElement(By.tagName("ul"));
				WebElement div = ul.findElement(By.tagName("div"));

				List<WebElement> options = div.findElements(By.tagName("a"));
				for (WebElement option : options) {
					if (option.getText().contains(txtIdConta.getText())) {
						option.click();
						break;
					}
				}
			}

		} catch (Exception e) {
			erroNoLogin = true;
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		if (!erroNoLogin) {
			criarDiretorioArquivo();
			manipularPutCallBinary();
			// manterLogado("gmt-clock");
			btnLogar.setEnabled(false);
		}
	}
}
