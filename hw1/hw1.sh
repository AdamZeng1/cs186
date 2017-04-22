#! /usr/bin/env bash
# bash command-line arguments are accessible as $0 (the bash script), $1, etc.
# echo "Running" $0 "on" $1

ebook='ebook.csv'
tokens='tokens.csv'
token_counts='token_counts.csv'
name_counts='name_counts.csv'
names='popular_names.txt'

crlf='sed s:$:\r:'

truncate -s 0 $ebook
echo 'ebook_id,token' | $crlf > $tokens
echo 'token,count' | $crlf | tee $token_counts > $name_counts

python parser.py $1 2>$ebook \
	| tee -a $tokens \
	| sed -r 's/([0-9]+),(\w+)/\2/g' \
	| sort \
	| uniq -c \
	| sed -r 's/\s*([0-9]+) (\w+)/\2,\1/g' \
	| tee -a $token_counts \
        | grep -i -e \
		`cat $names \
		| tr '\n' ' ' \
		| sed s/'\s'/', -e ^'/g` \
	>> $name_counts

exit 0
