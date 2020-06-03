#!/bin/bash
# Please run this script from inside your build directory
# bash ../babel/contrib/genalltests.sh
# msub alltests.msub
# squeue -u $USER
#partition=ubgl;  bank=asccasc
partition=udawn; bank=dev
cat <<EOF >alltests.msub
#!/bin/sh
##### Example Moab Job Control Script to be submitted with msub command
##### These lines are for Moab
#MSUB -l partition=$partition
#MSUB -l nodes=1
#MSUB -q pdebug
#MSUB -l walltime=10:00
#MSUB -A $bank
#MSUB -m be
#MSUB -e $PWD/stderr.txt
#MSUB -o $PWD/stdout.txt

##### These are shell commands
# Display job information for possible diagnostic use
date
echo "Job id = \$SLURM_JOBID"
hostname
sinfo
squeue -j \$SLURM_JOBID
cd $PWD
EOF

for TEST in examples/*/run*/run*[^\.sh] regression/*/run*/run*[^\.sh]
do cat <<EOF >>alltests.msub
echo "executing $TEST"
mpirun -np 1 -verbose 1 -mode vn -exe $TEST -cwd $PWD &
EOF
done

cat <<EOF >>alltests.msub
wait
echo 'Done'
date
EOF
