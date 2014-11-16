import os
import shutil
os.mkdir("./be-amplify");
lines = [line.strip() for line in open('./bkp/test.txt')]
count = 1
for i in lines:
	name = i.split(')');
	source = "./bkp/" + str(count) + ".wav"
	destination = "./be-amplify/" + name[0] + ".wav"
	shutil.copy(source, destination)
	count = count + 1
shutil.copy("./bkp/test.txt", "./be-amplify");

