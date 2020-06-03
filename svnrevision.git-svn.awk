BEGIN {
  REV=0;
  BRANCH="";
  MOD="";
}

END {
  print REV, MOD;
  print BRANCH;
}

/^URL:/ {
  BRANCH = $2;
  sub(/.*\//, "", BRANCH);
}

/^Last Changed Rev: / {
  REV=$4;
}
