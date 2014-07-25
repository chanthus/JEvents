package jevents.sample;

import javax.swing.UIManager;

public class Main {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		// Observable model
		Model model = new Model();

		ObservableFrame observable = new ObservableFrame(model);
		observable.setTitle("Observable");
		observable.setVisible(true);

		ObserverFrame observer = new ObserverFrame(model);
		observer.setTitle("Observer");

		int y = observable.getLocation().y;
		int x = observable.getLocation().x + observable.getWidth();

		observer.setLocation(x, y);
		observer.setVisible(true);
	}
}
