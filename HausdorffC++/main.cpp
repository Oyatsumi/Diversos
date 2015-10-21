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
	
	for (int k = 0; k < 1; k++){

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










void printCudaSpecs(){
	const int kb = 1024;
	const int mb = kb * kb;
	wcout << "NBody.GPU" << endl << "=========" << endl << endl;

	wcout << "CUDA version:   v" << CUDART_VERSION << endl;
	int devCount;
	cudaGetDeviceCount(&devCount);
	wcout << "CUDA Devices: " << endl << endl;

	for (int i = 0; i < devCount; ++i)
	{
		cudaDeviceProp props;
		cudaGetDeviceProperties(&props, i);
		wcout << i << ": " << props.name << ": " << props.major << "." << props.minor << endl;
		wcout << "  Global memory:   " << props.totalGlobalMem / mb << "mb" << endl;
		wcout << "  Shared memory:   " << props.sharedMemPerBlock / kb << "kb" << endl;
		wcout << "  Constant memory: " << props.totalConstMem / kb << "kb" << endl;
		wcout << "  Block registers: " << props.regsPerBlock << endl;
		wcout << "  Multiprocessors: " << props.multiProcessorCount << endl << endl;


		wcout << "  Warp size:         " << props.warpSize << endl;
		wcout << "  Threads per block: " << props.maxThreadsPerBlock << endl;
		wcout << "  Max block dimensions: [ " << props.maxThreadsDim[0] << ", " << props.maxThreadsDim[1] << ", " << props.maxThreadsDim[2] << " ]" << endl;
		wcout << "  Max grid dimensions:  [ " << props.maxGridSize[0] << ", " << props.maxGridSize[1] << ", " << props.maxGridSize[2] << " ]" << endl;
		wcout << endl;
	}
}

/*
CImg<unsigned char> src("test.bmp");
int width = src.width();
int height = src.height();
src(0, 0, 0, 0) = 100;
cout << width << "x" << height << endl;
for (int r = 0; r < height; r++)
for (int c = 0; c < width; c++)
cout << "(" << r << "," << c << ") ="
<< " R" << (int)src(c, r, 0, 0)
<< " G" << (int)src(c, r, 0, 1)
<< " B" << (int)src(c, r, 0, 2) << endl;

src.normalize(0, 255);
src.save("img2.bmp");
return 0;
*/
