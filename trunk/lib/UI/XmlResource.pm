package UI::XmlResource;

use strict;
use warnings;

use Wx;
use Wx::XRC;
use base qw( Wx::XmlResource );

sub new {
	my( $class ) = shift;
	my( $self ) = $class->SUPER::new( @_ );

	return $self;
}

1;
