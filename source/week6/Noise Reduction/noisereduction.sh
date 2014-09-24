sox input.wav -n trim 0 0.5 noiseprof myprofile
sox input.wav output.wav noisered myprofile 0.2


#the input file is named as "input.wav" and output file is named as "output.wav".
#we checked for different parameters out of which 0.2 gave the best results.
#the output files and wavesurfer screenshots are in test folder.
