BEGIN { MAXREV=0 ; LINE=""; MOD="" ; OFS="" }
END { print MAXREV, MOD }
/^([ADMC!~]|.[MC])/ { MOD="M" ; LINE=$0 }
/^\?/ { }
/^ *[0-9]/ { if ($2 > MAXREV) { MAXREV=$2 ; LINE=$0 } }
