package UI::Frame;

use strict;
use warnings;

use Wx;
use base qw( Wx::Frame );

sub new {
	my( $class, %params ) = @_;
	

	my( $self ) = $class->SUPER::new( 
		$params{'parent'}, 
		$params{'id'}, 
		$params{'title'}, 
		$params{'position'}, 
		$params{'dimension'} 
	);

	return $self;
}

1;
