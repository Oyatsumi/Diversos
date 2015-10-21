#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <iostream>
#include <Image.h>
#include <Hausdorff_common.h>
#include <chrono>

using namespace cv;
using namespace std;

int main(int argc, char** argv)
{

	auto begin = std::chrono::high_resolution_clock::now();
	
	for (int k = 0; k < 10; k++){

	String dir = "C:\\Users\\erick\\Documents\\Esteban\\GPU\\vectorAdd2\\bin\\8192\\";

	Image img1 = Image(dir + "1.bmp"),
		img2 = Image(dir + "2.bmp");


		//wcout << "Thrust version: v" << THRUST_MAJOR_VERSION << "." << THRUST_MINOR_VERSION << endl << endl;


	
		hdCPU(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img2.getHeight(), 2);


		//struct elements
		//const int SIZE = 1; //qtd total de blocos(SIZE + 1)*(SIZE + 1)
		//bool *structElement = new bool[(SIZE + 1)*(SIZE + 1)] { true, true, true, true }; //orientação: esquerda direita, cima baixo

		//hdCPUMorphology(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img1.getHeight(), structElement);

		//hdCPUSync(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img1.getHeight(), structElement, SIZE);

		//hdGPUSync(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img1.getHeight(), structElement, SIZE);

		//hdGPUSyncShared(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img1.getHeight(), structElement, SIZE);

		//ferrado \/
		//hdAsync(img1.getBooleanLinearImage(0), img2.getBooleanLinearImage(0), img1.getWidth(), img1.getHeight(), 1);

		//printf("bla %d", img1.getPixel(1, 0, 0));
		//img1.printBooleanLinearImage(0);
	}
	auto end = std::chrono::high_resolution_clock::now();

	std::cout << "Total elapsed time: ";
	std::cout << (double)(::chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count()/(double)1000000000) << "s" << std::endl;


	return 0;
}







