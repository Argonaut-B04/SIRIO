import React from 'react';
import SirioDetailPage from '../SirioDetailPage';
import SirioButton from '../../Button/SirioButton';
import SopService from '../../../Services/SopService';
import { NavLink } from 'react-router-dom';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import SirioWarningButton from "../../Button/ActionButton/SirioWarningButton";

class DetailSOP extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            sop: {},
            dataGeneral: {},
            redirect: false
        }

        this.renderDataSOP = this.renderDataSOP.bind(this);
        this.setRedirect = this.setRedirect.bind(this);
    }

    componentDidMount() {
        this.renderDataSOP();
    }

    setRedirect = () => {
        this.setState({
            redirect: true
        })
    };

    renderRedirect = () => {
        if (this.state.redirect) {
            return <Redirect to={{
                pathname: "/manager/sop",
                state: {
                    deleteSuccess: true
                }
            }} />
        }
    };

    async renderDataSOP() {
        console.log(this.props.location.state.id)
        const response = await SopService.getSOPDetail(1);
        
        this.setState({
           sop: response.data.result,
           dataGeneral: {
            "Judul SOP       :": response.data.result.judul,
            "Kategori SOP    :": response.data.result.kategori,
            "Link SOP        :": response.data.result.linkDokumen,
         }
        })
    }

    hapus(id) {
        const sop = {
            id: id
        };
        SopService.deleteSOP(sop)
            .then(() => this.setRedirect());
    }
   
    subButton(status) {
        if (status === "AKTIF"){
            return (
                <div>
                    <NavLink to={{
                        pathname: "/manager/sop/ubah",
                        state: {
                            id: this.state.sop.idSop,
                        }
                    }}>
                        <SirioButton
                            purple
                        >
                            Ubah
                        </SirioButton>
                    </NavLink>
                    <SirioWarningButton
                        red
                        modalTitle="Konfirmasi Penghapusan"
                        modalDesc="Apakah anda yakin untuk menghapus sop?"
                        onConfirm={() => this.hapus(this.state.sop.idSop)}
                        customConfirmText="Ya, Hapus"
                        customCancelText="Batal"
                    >
                        Hapus
                    </SirioWarningButton>
                </div>
                
            )
        }else{
            return (
                <div>
                   
                </div>
                
            )
        }
        
    }

    render() {
        return (
            <>
                {this.renderRedirect()}
                <SirioDetailPage
                    title="Detail SOP"
                    data={this.state.dataGeneral}
                    id='id'
                    subButton={this.subButton(this.state.sop.status)}
                    link="manager/sop"
                />
            </>
        );
    }
}

export default withRouter(DetailSOP);