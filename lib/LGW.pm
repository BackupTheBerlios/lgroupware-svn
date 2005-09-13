package LGW;

use strict;
use warnings;

use UI;
use UI::App;
use UI::Frame;
use UI::BoxSizer;
use UI::SplitterWindow;
use UI::Panel;
use UI::Window;

sub new {
	my( $class, %params ) = @_;
	my $self = {};
	
	bless( $self, $class );

	# create main application window
	$self->{'main'}{'frame'} = UI::Frame->new(
		parent		=> undef,
		id		=> -1,
		title		=> 'der Titel',
		position	=> UI::getDefaultPosition(),
		dimension	=> [640, 480]
		);
	
	# create a vertical layout
	$self->{'main'}{'box'} = UI::BoxSizer->new( direction => UI::getVerticalDirection() );
	$self->{'window'}{'top'} = UI::Panel->new( parent => $self->{'main'}{'frame'}, id => -1 );

	# create a splitter window for dynamic sizing of the working area
	$self->{'window'}{'bottom'} = UI::SplitterWindow->new( $self->{'main'}{'frame'}, -1, UI::getDefaultPosition(), [300, 300], UI::getNoFullRepaintOnResize() | UI::getClipChildren() );

	$self->{'window'}{'top'}->SetBackgroundColour( UI::getColour( 133, 133, 133 ) );
	$self->{'window'}{'top'}->SetSize( -1, 150 );

	$self->{'main'}{'box'}->Add( $self->{'window'}{'top'}, 0, UI::getGrow() );
	$self->{'main'}{'box'}->Add( $self->{'window'}{'bottom'}, 2, UI::getGrow() );

	$self->{'main'}{'frame'}->SetSizer( $self->{'main'}{'box'} );

	# create left and right working areas
	$self->{'workarea'}{'left'} = UI::Window->new( parent => $self->{'window'}{'bottom'}, id => -1 );
	$self->{'workarea'}{'right'} = UI::Window->new( parent => $self->{'window'}{'bottom'}, id=> -1 );

	$self->{'workarea'}{'left'}->SetBackgroundColour( UI::getColour( 133, 0, 0 ) );
	$self->{'workarea'}{'left'}->SetSize( 300, -1 );

	$self->{'window'}{'bottom'}->SplitVertically( $self->{'workarea'}{'left'}, $self->{'workarea'}{'right'} );
	
	# run the application
	$self->{'ui'} = UI::App->new( mainframe => $self->{'main'}{'frame'});
	$self->{'ui'}->MainLoop();
	
	return $self;
}

1;
