package UI::SplitterWindow;

use strict;
use warnings;

use Wx;
use base qw( Wx::SplitterWindow );

sub new {
	my( $self ) = shift->SUPER::new( @_ );

	return $self;
}

1;
