#!usr/bin/bash


parse_args() {
	while [ "$#" -gt 0 ]; do
		case $1 in
			--auto)
				echo "args = auto..."
				shift
				;;
			--addclient)
				echo "args = addclient..."
				shift
				shift
				;;
			--exportclient)
				echo "args = exportclient..."
                shift
				shift
				;;
			--listclients)
				echo "args = listclients..."
				shift
				;;
			--revokeclient)
				echo "args = revokeclient..."
				unsanitized_client="$2"
				shift
				shift
				;;
			--uninstall)
				echo "args = unistall..."
				shift
				;;
			-y|--yes)
				echo "args = yes..."
				shift
				;;
			-h|--help)
				echo "args = help..."
				;;
			*)
				echo "args = *..."
				;;
		esac
	done
}

parse_args "$@"
