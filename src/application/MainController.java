package application;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import characteristics_extrator.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MainController {
	double[] caracteristicas;

	ArrayList<Double> characteriscts = new ArrayList<Double>();
	
	@FXML
	private ImageView imageView;
	
//	Ned Flunders infos
	@FXML private TextField txtCamisaNed;
	@FXML private TextField txtMoletomNed;
	@FXML private TextField txtCalcaNed;
	@FXML private TextField txtCabeloNed;
	@FXML private TextField txtTotalNed;
	
//	Diretor Skinner infos
	@FXML private TextField txtCamisaSkinner;
	@FXML private TextField txtPaletoSkinner;
	@FXML private TextField txtCalcaSkinner;
	@FXML private TextField txtCabeloSkinner;
	@FXML private TextField txtGravataSkinner;
	@FXML private TextField txtTotalSkinner;
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}
	
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			caracteristicas = ExtractCaracteristicas.extraiCaracteristicas(f);

			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.UP);

			txtCamisaNed.setText(df.format(caracteristicas[0]) + "%");
			txtMoletomNed.setText(df.format(caracteristicas[1]) + "%");
			txtCalcaNed.setText(df.format(caracteristicas[2]) + "%");
			txtCabeloNed.setText(df.format(caracteristicas[3]) + "%");
			
			txtCamisaSkinner.setText(df.format(caracteristicas[4]) + "%");
			txtPaletoSkinner.setText(df.format(caracteristicas[5]) + "%");
			txtCalcaSkinner.setText(df.format(caracteristicas[6]) + "%");
			txtCabeloSkinner.setText(df.format(caracteristicas[7]) + "%");
			txtGravataSkinner.setText(df.format(caracteristicas[8]) + "%");
			
			double[] nb = naiveBayes(caracteristicas);
			txtTotalNed.setText(df.format((nb[0] * 100)) + "%");
			txtTotalSkinner.setText(df.format((nb[1] * 100)) + "%");
		}
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imgs"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
	public static double[] naiveBayes(double[]caracteristicas) {
		double[] retorno = {0,0};
		try {
			//*******carregar arquivo de características
			DataSource ds = new DataSource("caracteristicas.arff");
			Instances instancias = ds.getDataSet();
			//instancias.setClassIndex(6);
			instancias.setClassIndex(instancias.numAttributes()-1);
			
			//Classifica com base nas características da imagem selecionada
			NaiveBayes nb = new NaiveBayes();
			nb.buildClassifier(instancias);//aprendizagem (tabela de probabilidades)
			
			
			Instance novo = new DenseInstance(instancias.numAttributes());
			novo.setDataset(instancias);
			novo.setValue(0, caracteristicas[0]);
			novo.setValue(1, caracteristicas[1]);
			novo.setValue(2, caracteristicas[2]);
			novo.setValue(3, caracteristicas[3]);
			novo.setValue(4, caracteristicas[4]);
			novo.setValue(5, caracteristicas[5]);
			novo.setValue(6, caracteristicas[6]);
			novo.setValue(7, caracteristicas[7]);
			novo.setValue(8, caracteristicas[8]);
			
			retorno = nb.distributionForInstance(novo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}


