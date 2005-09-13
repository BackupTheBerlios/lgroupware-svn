package UI;

use strict;
use warnings;

use Wx qw( wxDefaultSize wxDefaultPosition wxHORIZONTAL wxVERTICAL wxGROW 
	   wxNO_FULL_REPAINT_ON_RESIZE wxCLIP_CHILDREN );
use Wx;

sub getDefaultSize {
	return wxDefaultSize;
}

sub getDefaultPosition {
	return wxDefaultPosition;
}

sub getColour {
	return Wx::Colour->new( @_ );
}

sub getHorizontalDirection {
	return wxHORIZONTAL;
}

sub getVerticalDirection {
	return wxVERTICAL;
}

sub getGrow {
	return wxGROW;
}

sub getNoFullRepaintOnResize {
	return wxNO_FULL_REPAINT_ON_RESIZE;
}

sub getClipChildren {
	return wxCLIP_CHILDREN;
}

1;
